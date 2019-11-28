use std::io::prelude::*;
use std::net::{TcpStream};
use ssh2::Session;

/// ssh 登录并执行命令
/// 参考 https://docs.rs/ssh2/0.5.0/ssh2/
/// proxyJump 无法实现
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