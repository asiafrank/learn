// db 全局配置

use std::sync::RwLock;
use std::sync::Mutex;
use config::*;
use std::fs;
use std::io::{ErrorKind,SeekFrom,Seek};
use std::mem::transmute;

//pub static VERSION: &str = "version";
pub static DBFILE_KEY: &str = "dbfile";

lazy_static! {
    static ref DBCONFIG: RwLock<Config> = RwLock::new(Config::default());

    // 全局 DB 文件
    static ref DBFILE: Mutex<fs::File> = {
        // 打开 db 二进制文件
        let db_file_name = match get_string(DBFILE_KEY) {
            Ok(s) => s,
            Err(e) => {
                eprintln!("{}", e);
                String::from("")
            }
        };
        match fs::File::open(&db_file_name) {
            Ok(f) => Mutex::new(f),
            Err(e) => {
                match e.kind() { // 文件不存在则新建
                    ErrorKind::NotFound => {
                        let f = fs::File::create(&db_file_name).unwrap();
                        Mutex::new(f)
                    },
                    _ => panic!("db file unknown error")
                }
            }
        }
    };
}

pub fn config_init() {
    // 加载配置文件
    let mut c = DBCONFIG.write().unwrap();
    c.merge(config::File::with_name("conf/default.toml")).unwrap();
}

pub fn get_string(prop_key: &str) -> Result<String, ConfigError> {
    let c = DBCONFIG.read().unwrap();
    c.get_str(prop_key)
}

// db 文件相关

/// 将字节码存入文件
pub fn write_record_bytes(record_bytes: &Vec<u8>) {
    use std::io::Write;
    DBFILE.lock().unwrap().write_all(record_bytes.as_ref()).unwrap();
}

// TODO: get abc 实验，返回 123
pub fn read_record_bytes(key: &str) -> Result<Vec<u8>, String> {
    use std::io::Read;
    let mut f = DBFILE.lock().unwrap();

    let mut seeked_bytes_num: u64 = 0;
    let mut current_total_size: u64 = 0;
    let mut current_key_size: u16 = 0;
    let mut current_value_size: u32 = 0;

    let mut total_size_buf = [0; 8];
    let mut key_size_buf = [0; 2];
    let mut value_size_buf = [0; 4];
    let x = loop {
        println!("loop...... {}", key);
        match f.seek(SeekFrom::Start(seeked_bytes_num)) {
            Ok(m) => {
                // 找 total size
                f.read_exact(&mut total_size_buf);
                current_total_size = unsafe {
                    transmute::<[u8; 8], u64>(total_size_buf)
                };

                // 找 key size
                f.read_exact(&mut key_size_buf);
                current_key_size = unsafe {
                    transmute::<[u8; 2], u16>(key_size_buf)
                };

                // 获取 key content
                let mut key_buf: Vec<u8> = Vec::with_capacity(current_key_size as usize);
                let mut handle = f.by_ref().take(current_key_size as u64);
                handle.read_to_end(&mut key_buf);

                let key_bytes = key.as_bytes();
                let key_bytes_size = key_bytes.len() as u16;

                // key content 比较
                if key_bytes_size == current_key_size {
                    let mut is_match = true;
                    let mut index: usize = 0;
                    while index < key_bytes_size as usize {
                        let k0 = key_bytes[index];
                        let k1 = key_buf[index];

                        println!("loop...... {}, {}", k0, k1);

                        if key_bytes[index] != key_buf[index] {
                            is_match = false;
                            break
                        }
                        index = index + 1;
                    }

                    if is_match { // key 匹配，则查找 value
                        // 获取 value size
                        f.read_exact(&mut value_size_buf);
                        current_value_size = unsafe {
                            transmute::<[u8; 4], u32>(value_size_buf)
                        };

                        // 获取 value content
                        let mut value_buf: Vec<u8> = Vec::with_capacity(current_value_size as usize);
                        let mut value_handle = f.by_ref().take(current_value_size as u64);
                        value_handle.read_to_end(&mut value_buf);

                        break Ok(value_buf)
                    }
                }
            },
            Err(e) => {
                eprintln!("{}", e);
                break Err(String::from("Nothing found"));
            }
        }
        seeked_bytes_num = seeked_bytes_num + current_total_size;
    };
    x
}