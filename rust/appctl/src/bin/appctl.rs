extern crate structopt;
extern crate serde;
extern crate serde_yaml;

use structopt::StructOpt;
use serde::{Serialize, Deserialize};
use std::fs::File;
use std::io::{Read, BufReader, SeekFrom, Seek, BufRead};
use std::{io, fs};
use std::process::Command;
use std::thread::sleep;
use std::time::Duration;

#[derive(Debug, StructOpt)]
#[structopt(about = "appctl start/stop/backup", version = "0.0.1")]
pub struct Opt {
    /// Config file name
    #[structopt(short="f")]
    pub config_file: String,

}

#[derive(Debug, PartialEq, Serialize, Deserialize)]
pub struct Config {
    pub start: String,
    pub file_path: String,
    pub output_file: String,
    pub output_end_flag: String,
}

#[allow(unused_variables)]
fn main() -> io::Result<()> {
    let opt = Opt::from_args();
    let config_file = opt.config_file.clone();

    let mut file = File::open(config_file).unwrap();
    let mut contents = String::new();
    file.read_to_string(&mut contents).unwrap();
    println!("{:?}", contents);

    let config: Config = serde_yaml::from_str(&contents).unwrap();

    println!("{:?}", config);

    // TODO: 后台 config 中定义的程序
    let output = if cfg!(target_os = "windows") {
        Command::new("cmd")
            .args(&["/C", &config.start])
            .output()
            .expect("failed to execute process")
    } else {
        Command::new("sh")
            .arg("-c")
            .arg(config.start)
            .output()
            .expect("failed to execute process")
    };

    // TODO: 确保 file exists, 然后再执行接下来的逻辑
    sleep(Duration::from_millis(200));

    let hello = output.stdout;
    let hello_str = String::from_utf8(hello).unwrap();
    println!("----{}", hello_str);

    let metadata = fs::metadata(&config.output_file)?;
    let file_size = metadata.len();

    let mut buf = String::new();
    let mut seek_pos = file_size;

    loop {
        let file = File::open(&config.output_file).unwrap();
        let mut reader = BufReader::new(file);
        reader.seek(SeekFrom::Start(seek_pos)).unwrap();

        let read_size = reader.read_line(&mut buf).unwrap();
        if read_size > 0 {
            print!("{}", buf);

            if buf.contains(&config.output_end_flag) {
                break
            }
        }

        buf.clear();
        seek_pos = seek_pos + read_size as u64;

        sleep(Duration::from_millis(50));
    }

    Ok(())
}