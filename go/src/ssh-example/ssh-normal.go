package main

import (
	"flag"
	"fmt"
	"golang.org/x/crypto/ssh"
	kh "golang.org/x/crypto/ssh/knownhosts"
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
	"ssh-example/config"
	"ssh-example/scpclient"
	"time"
)

var configFileName string

func init() {
	flag.StringVar(&configFileName, "c", "", "config file")
}

func isFlagPassed(name string) bool {
	found := false
	flag.Visit(func(f *flag.Flag) {
		if f.Name == name {
			found = true
		}
	})
	return found
}

// ssh-normal -c scp-example.yaml
func main() {
	// TODO: 修改 scp 库，
	//      1.要求 CopyFile 能返回百分比
	//      2.要求 ScpClient 支持设置 conn, 用于 jump server 的配置

	flag.Parse()
	passed := isFlagPassed("c")
	if !passed {
		fmt.Println("please specify -c param")
		return
	}

	f, err := os.Open(configFileName)
	if err != nil {
		log.Fatal(err)
	}
	decoder := yaml.NewDecoder(f)

	scpConfig := config.ScpConfig{}
	err = decoder.Decode(&scpConfig)
	if err != nil {
		log.Fatal(err)
	}

	err = ConnectAndSendFile(&scpConfig)
	if err != nil {
		log.Fatal(err)
	}
}

func ConnectAndSendFile(scpConfig *config.ScpConfig) error {
	// 连上跳板机
	var jumpServerClient *ssh.Client
	if scpConfig.JumpServer.Enable {
		js := &scpConfig.JumpServer
		jsId := &js.Identify
		if jsId.FilePath != "" {
			key, err := ioutil.ReadFile(jsId.FilePath)
			if err != nil {
				return err
			}
			signer, err := ssh.ParsePrivateKeyWithPassphrase(key, []byte(jsId.Passphrase))
			if err != nil {
				return err
			}

			hostKeyCallback, err := kh.New("/Users/asiafrank/.ssh/known_hosts")
			jsConfig := &ssh.ClientConfig{
				User: js.User,
				Auth: []ssh.AuthMethod{
					// Add in password check here for moar security.
					ssh.PublicKeys(signer),
				},
				HostKeyCallback: hostKeyCallback,
			}

			jsAddress := fmt.Sprintf("%v:%v", js.Host, js.Port)
			jumpServerClient, err = ssh.Dial("tcp", jsAddress, jsConfig)
			if err != nil {
				return err
			}
			defer jumpServerClient.Close()
		}
	}

	// connect to server
	address := fmt.Sprintf("%v:%v", scpConfig.Host, scpConfig.Port)
	clientConfig := &ssh.ClientConfig{
		User: scpConfig.User,
		Auth: []ssh.AuthMethod{
			ssh.Password(scpConfig.Password),
		},
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}
	//client := scp.NewClient(address, config)
	client := scpclient.ScpClient{
		Host:         address,
		ClientConfig: clientConfig,
		Timeout:      time.Minute,
		RemoteBinary: "scp",
	}

	if jumpServerClient != nil {
		dailConn, err := jumpServerClient.Dial("tcp", address)
		if err != nil {
			return err
		}
		realServerConn, chans, reqs, err := ssh.NewClientConn(dailConn, address, client.ClientConfig)
		if err != nil {
			return err
		}
		realServerClient := ssh.NewClient(realServerConn, chans, reqs)
		realServerSession, err := realServerClient.NewSession()
		if err != nil {
			log.Fatal("unable to create SSH session: ", err)
		}
		defer realServerSession.Close()

		client.Conn = realServerConn
		client.Session = realServerSession
	} else {
		// Connect to the remote server
		err := client.Connect()
		if err != nil {
			fmt.Println("Couldn't establish a connection to the remote server ", err)
			return err
		}
	}

	// Open a file
	localFile, _ := os.Open(scpConfig.Scp.LocalFilePath)

	// Close client connection after the file has been copied
	defer client.Close()

	// Close the file after it has been copied
	defer localFile.Close()

	// Finaly, copy the file over
	// Usage: CopyFile(fileReader, remotePath, permission)

	err := client.CopyFile(localFile, scpConfig.Scp.DestFilePath, scpConfig.Scp.Permission)
	if err != nil {
		// fmt.Println("Error while copying file ", err)
		return err
	}
	return err
}
