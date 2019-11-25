// db 全局配置

use std::sync::RwLock;
use std::sync::{Mutex,MutexGuard};
use config::*;
use std::fs;
use std::fs::OpenOptions;

//pub static VERSION: &str = "version";
pub static DBFILE_KEY: &str = "dbfile";

lazy_static! {
    static ref DBCONFIG: RwLock<Config> = RwLock::new(Config::default());

    // 全局 DB 写文件
    static ref DBFILE_WRITE: Mutex<fs::File> = init_mutex_db_file_write();
}

fn get_db_file_name() -> String {
    get_string(DBFILE_KEY).unwrap_or_else(|e| {
        eprintln!("{}", e);
        String::from("xdb_default.bin")
    })
}

/// 只在 lazy_static! 调用一次
/// 初始化 DBFILE_WRITE
fn init_mutex_db_file_write() -> Mutex<fs::File> {
    let db_file_name = get_db_file_name();

    // 打开 db 二进制文件
    let write_file = OpenOptions::new()
        .read(true)
        .append(true)
        .create(true)
        .open(db_file_name)
        .unwrap();
    Mutex::new(write_file)
}

/// 获取文件锁，并返回文件实例
pub fn get_db_file_write_instance() -> MutexGuard<'static, fs::File> {
    DBFILE_WRITE.lock().unwrap()
}

/// 获取只读文件实例，无锁
pub fn get_db_file_read_instance() -> fs::File {
    let db_file_name = get_db_file_name();

    OpenOptions::new()
        .read(true)
        .open(db_file_name)
        .unwrap()
}

/// 系统配置初始化，在 main 中调用
pub fn config_init() {
    // 加载配置文件
    let mut c = DBCONFIG.write().unwrap();
    c.merge(config::File::with_name("conf/default.toml")).unwrap();
}

/// 获取系统配置 String value
pub fn get_string(prop_key: &str) -> Result<String, ConfigError> {
    let c = DBCONFIG.read().unwrap();
    c.get_str(prop_key)
}