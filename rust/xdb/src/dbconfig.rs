// db 全局配置

use std::sync::RwLock;
use std::sync::Mutex;
use config::*;
use std::fs;
use std::io::{ErrorKind};

//pub static VERSION: &str = "version";
pub static DBFILE_KEY: &str = "dbfile";

lazy_static! {
    static ref DBCONFIG: RwLock<Config> = RwLock::new(Config::default());

    // 全局 DB 文件
    pub static ref DBFILE: Mutex<fs::File> = {
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