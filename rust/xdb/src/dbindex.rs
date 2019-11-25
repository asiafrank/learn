// TODO: 索引文件的维护

use crate::dbconfig::{get_db_index_read_instance, get_db_file_read_instance};
use std::sync::RwLock;
use std::collections::HashMap;
use std::io::{BufReader, BufRead};

// TODO: 先用 RWLock 保证线程安全，直接用 HashMap 做.
//       有空再抄 java 的 ConcurrentHashMap

lazy_static! {
    static ref DBINDEX_MAP: RwLock<HashMap> = RwLock::new(HashMap::new());
}

/// 初始化 DBINDEX_MAP
/// 索引文件格式：
/// ```
/// ------------------------------------------------
/// total size | key size | key content | offset
/// ------------------------------------------------
/// offset: 8 字节大小, 指示该 key 对应记录在 dbfile 中的位置
/// ```
pub fn dbindex_init() {
    let dbindex_file = get_db_file_read_instance();
    let mut reader = BufReader::new(dbindex_file);
    let mut buf = String::new();

    let mut map = DBINDEX_MAP.lock().unwrap();
    loop {
        let bytes_num = reader.read_line(&mut buf).unwrap();
        if bytes_num == 0 { // EOF
            break
        }
//      TODO: 设计好 索引文件格式，读取索引
//      map.insert();

        buf.clear();
    }
}