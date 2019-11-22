#[macro_use]
extern crate lazy_static;
extern crate config;

mod dbconfig;
mod cmds;
mod cmd_line;

use dbconfig::config_init;
use cmd_line::get_cmd_line;
use cmds::action;

// TODO: 实现 p72 页 最简单的 DB
//       1.维护 set get 命令
//       2.追加写入文件，key，value二进制存储，key大小|key|value大小|value 这样的格式
//       3.维护索引文件，启动时加载，定时刷盘
//       4.实现预写日志
//       5.使用 tokio 提供网络服务

fn main() {
    config_init();
    println!("Well come to xdb!");

    loop {
        let cmd_line = get_cmd_line();
        action(&cmd_line);
    }
}
