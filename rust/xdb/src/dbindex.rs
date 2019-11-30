// TODO: 索引文件的维护

use crate::dbconfig::{get_db_index_read_instance, get_db_file_read_instance};
use std::sync::RwLock;
use std::collections::HashMap;
use std::io::Read;
use std::mem::transmute;
use std::fs::File;

// TODO: 先用 RWLock 保证线程安全，直接用 HashMap 做.
//       有空再抄 java 的 ConcurrentHashMap

lazy_static! {
    static ref DBINDEX_MAP: RwLock<HashMap<String, u64>> = RwLock::new(HashMap::new());
}

/// 初始化 DBINDEX_MAP
/// 索引文件格式：
/// ```
/// ------------------------------------------------
/// total size | key size | key content | offset
/// ------------------------------------------------
/// `total size`: 8字节大小，
/// `key size`: 2 个字节
/// `key content`: key 内容，大小由 key size 决定
/// `offset`: 8 字节大小, 指示该 key 对应记录在 dbfile 中的位置
/// ```
/// 1.从文件中读取 8字节，获取 total size 的值
/// 2.从文件中读取 2字节，获取 key size 的值
/// 3.从文件中读取 key size 大小的字节，获取 key content 转换为 string
/// 4.从文件中读取 8字节大小，获取 offset 值
pub fn dbindex_init() {
    let mut dbindex_file = get_db_index_read_instance();

    let mut map = DBINDEX_MAP.write().unwrap();
    let mut buf = Vec::new();

    let total_size_len = 8;
    let key_size_len = 2;
    let offset_len = 2;
    let mut current_total_len;
    let mut current_key_len;
    loop {
        // 1. 获取 current_total_size
        let mut bytes_num = take_bytes(&mut dbindex_file, &mut buf, total_size_len);
        if bytes_num == 0 { // EOF
            break
        }
        current_total_len = bytes_to_u64(buf.as_ref());

        // 2. 获取 current_key_size
        bytes_num = take_bytes(&mut dbindex_file, &mut buf, key_size_len);
        // 这里不会出现 bytes_num = 0 的情况，如果出现则必定是文件损坏
        if bytes_num == 0 {
            panic!("dbindex file take current_key_size EOF occur, dbindex file must be broken");
        }
        current_key_len = bytes_to_u64(buf.as_ref());

        // 3. 获取 key content
        bytes_num = take_bytes(&mut dbindex_file, &mut buf, current_key_len);
        if bytes_num == 0 {
            panic!("dbindex file take key content EOF occur, dbindex file must be broken");
        }
        let key_content = String::from_utf8_lossy(buf.as_ref()).to_string();

        // 4. 获取 offset 值
        bytes_num = take_bytes(&mut dbindex_file, &mut buf, offset_len);
        if bytes_num == 0 {
            panic!("dbindex file take offset EOF occur, dbindex file must be broken");
        }

        let offset = bytes_to_u64(buf.as_ref());
        map.insert(key_content, offset);

        buf.clear();
    }
}

/// 从文件中读取相应字节，放入 `buf` 中
fn take_bytes(dbindex_file: &mut File, buf: &mut Vec<u8>, take_num: u64) -> usize {
    let mut handle = dbindex_file.by_ref().take(take_num);
    handle.read_to_end(buf).unwrap()
}

/// 64 bit 数，转换为 8 元素数组
fn u64_to_bytes(num: u64) -> [u8; 8] {
    let b = unsafe {
        transmute::<u64, [u8; 8]>(num)
    };
    b
}

/// 8 个元素的 byte 数组，转换为 u64
fn bytes_to_u64(buf: &[u8]) -> u64 {
    let mut x : u64 = 0;
    x = x & buf[0] as u64;
    x = (x << 8) & buf[1] as u64;
    x = (x << 8) & buf[2] as u64;
    x = (x << 8) & buf[3] as u64;
    x = (x << 8) & buf[4] as u64;
    x = (x << 8) & buf[5] as u64;
    x = (x << 8) & buf[6] as u64;
    x = (x << 8) & buf[7] as u64;
    x
}

#[test]
fn test_take_bytes() {
    let mut f = File::open("conf/default.toml").unwrap();
    let mut buf = Vec::new();
    let bytes_num = take_bytes(&mut f, &mut buf, 17);

    let str = String::from_utf8_lossy(buf.as_ref()).to_string();
    println!("{}", str);
    assert_eq!(bytes_num, 17);
}