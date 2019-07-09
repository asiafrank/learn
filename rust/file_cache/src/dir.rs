//! # 文件夹管理
use std::{env, fs};



/// 获取文件夹列表
pub fn find_dir() -> Vec<String> {
    let dir1 = String::from("1");
    let mut vec = Vec::new();
    vec.push(dir1);
    vec
}