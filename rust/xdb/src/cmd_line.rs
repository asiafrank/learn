use std::io::{self, Write};
use crate::cmds::SUPPORTED_CMD;

/// 获取输入的命令，匹配输入的格式是否正确，
/// 如果不正确则打印提示，再次要求输入，
/// 直到匹配正确的命令，返回 CmdLine 对象
pub fn get_cmd_line() -> CmdLine {
    let cmd_line = loop {
        let line = prompt();
        match CmdLine::new(line) {
            Ok(x) => break x,
            Err(e) => eprintln!("{}", e),
        }
    };
    cmd_line
}

/// 打印 `xdb>` 提示符，用来提示用户输入命令
pub fn prompt() -> String {
    print!("xdb> ");
    io::stdout().flush().unwrap();

    let mut line = String::new();
    io::stdin().read_line(&mut line)
        .expect("Failed to read line");
    line
}

//------- 下面是结构体 ----------------

/// key value pair, 主要用于 set 命令
pub struct Pair {
    pub key: String,
    pub value: Vec<u8>,
}

/// 代表用户输入的包装类
/// 只有正确的命令才能包装成类
pub struct CmdLine {
    pub command: String,
    pub pair: Pair,
}

impl CmdLine {
    /// 解析用户输入，包装成 CmdLine 对象。
    /// 这里将严格校验用户的输入，
    /// 只有正确的输入才会 Ok 并返回 CmdLine 对象
    pub fn new(cmd_line: String) -> Result<Self, String> {
        let mut it = cmd_line.split_whitespace();
        let cmd = match it.next() {
            Some(x) => x,
            None => return Err(String::from("command not exists")),
        };

        if !SUPPORTED_CMD.contains(cmd) {
            return Err(format!("unsupported command {}", cmd))
        }

        let key = match it.next() {
            Some(x) => x,
            None => return Err(String::from("key not exists")),
        };

        let value = match it.next() {
            Some(v) => v,
            None => "",
        };

        let pair = Pair {
            key: String::from(key),
            value: Vec::from(value.as_bytes())
        };

        Ok(CmdLine {
            command: String::from(cmd),
            pair,
        })
    }
}