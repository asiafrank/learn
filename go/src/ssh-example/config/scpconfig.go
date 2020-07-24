package config

// 私钥配置
type SshIdentify struct {
	// 私钥文件路径
	FilePath string `yaml:"filePath"`
	// 解开私钥的密码
	Passphrase string `yaml:"passphrase"`
}

// yaml配置, 用来 scp 传输文件
type ScpConfig struct {
	Host     string
	Port     int
	User     string
	Password string

	Identify SshIdentify

	JumpServer struct {
		Enable   bool        `yaml:"enable"`
		Host     string      `yaml:"host"`
		Port     int         `yaml:"port"`
		User     string      `yaml:"user"`
		Password string      `yaml:"password"`
		Identify SshIdentify `yaml:"identify"`
	} `yaml:"jumpServer"`

	Scp struct {
		LocalFilePath string `yaml:"localFilePath"`
		DestFilePath  string `yaml:"destFilePath"`
		Permission    string `yaml:"permission"`
	}
}
