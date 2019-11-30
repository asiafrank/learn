use std::io::prelude::*;
use std::net::{TcpStream};
use ssh2::Session;
use std::process::Command;
use std::io::{Write};

/// ssh 登录并执行命令
/// 参考 https://docs.rs/ssh2/0.5.0/ssh2/
///
/// 注：proxyJump 实现困难，要重新造轮子。因此只能通过 ssh config 配置，以及 ssh 命令行调用
/// 来实现。ssh config 配置见 https://en.wikibooks.org/wiki/OpenSSH/Cookbook/Proxies_and_Jump_Hosts
/// TODO: ssh agent 尝试
///
pub fn ssh_example_1() {
    // Connect to the local SSH server
    let tcp = TcpStream::connect("127.0.0.1:22").unwrap();
    let mut sess = Session::new().unwrap();
    sess.set_tcp_stream(tcp);
    sess.handshake().unwrap();
    sess.userauth_agent("username").unwrap();
    // sess.userauth_pubkey_file() 支持文件

    let mut channel = sess.channel_session().unwrap();
    channel.exec("ls").unwrap();
    let mut s = String::new();
    channel.read_to_string(&mut s).unwrap();
    println!("{}", s);
    channel.wait_close();
    println!("{}", channel.exit_status().unwrap());
}