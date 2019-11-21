use std::collections::{HashSet};
use crate::cmd_line::{CmdLine, Pair};
use crate::dbconfig::{write_record_bytes, read_record_bytes};
use std::mem::transmute;

// 支持的命令及对应命令的实现

lazy_static! {
    pub static ref SUPPORTED_CMD: HashSet<&'static str> = {
        let mut set = HashSet::new();
        set.insert("set");
        set.insert("get");
        set
    };
}

/// 命令的执行
pub fn action(cmd_line: &CmdLine) {
    let command = cmd_line.command.as_str();
    match command {
        "set" => set_action(&cmd_line.pair),
        "get" => get_action(&cmd_line.pair),
        _ => eprintln!("not command found and no action")
    }
}

/// set：将 key,value 存入文件
/// 存储文件格式：
/// ```
/// ----------------------------------------------------------------
/// total size | key size | key content | value size | value content
/// ----------------------------------------------------------------
/// ```
/// - `total size`：记录总大小，8个字节，包含 total size,key size,key content,value size,value content 大小
/// - `key size`：key 大小，2个字节，即一个 key 最大可以包含 65536 个字节(64KB)
/// - `key content`：key 内容大小由 `key size` 决定
/// - `value size`：value 大小，4个字节，即一个 value 最大可以包含 4294967296 个字节(4GB)
/// - `value content`：value 内容大小由 `value size` 决定
fn set_action(pair: &Pair) {
    // 写入文件
    let key_bytes = pair.key.as_bytes();
    let key_size = key_bytes.len();
    if key_size > 65536 {
        eprintln!("key size is bigger than 65536 Bytes");
        return
    }
    // 转换为 u8 数组时，是按 little endian 来排列的
    // 故针对 64位平台的 usize，0 个元素是低位，
    // 我们只需取 0,1 个元素即可
    let key_size_bytes = unsafe {
        transmute::<usize, [u8; 8]>(key_size)
    };

    let value_bytes = pair.value.as_slice();
    let value_size = value_bytes.len();
    let value_size_bytes = unsafe {
        transmute::<usize, [u8; 8]>(value_size)
    };

    // 8 字节的 total size, 2 字节的 key size, 4 字节的 value size
    let total_size = key_size + value_size + 8 + 2 + 4;
    let total_size_bytes = unsafe {
        transmute::<usize, [u8; 8]>(total_size)
    };

    // 组合 key 和 value，形成记录 bytes
    let mut record_bytes: Vec<u8> = Vec::new();
    // total size
    for b in total_size_bytes.iter() {
        record_bytes.push(*b);
    }

    // key size
    record_bytes.push(key_size_bytes[0]);
    record_bytes.push(key_size_bytes[1]);
    // key content
    for b in key_bytes {
        record_bytes.push(*b);
    }

    // value size
    record_bytes.push(value_size_bytes[0]);
    record_bytes.push(value_size_bytes[1]);
    record_bytes.push(value_size_bytes[2]);
    record_bytes.push(value_size_bytes[4]);
    // value content
    for b in value_bytes {
        record_bytes.push(*b);
    }
    write_record_bytes(&record_bytes);
}

/// get：从文件中获取 value
fn get_action(pair: &Pair) {
    read_record_bytes(&pair.key);
}