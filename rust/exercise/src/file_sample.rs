use std::fs::File;
use std::io::prelude::*;
use std::io::{Error, ErrorKind};

/// 文件读写例子

pub fn file_sample() {
    file_write_1().unwrap();
    open_with_option();
}

/// 简单的文件写
fn file_write_1() -> Result<(), Error> {
    let mut write = File::open("xyz.txt").unwrap_or_else(|error| {
        if error.kind() == ErrorKind::NotFound {
            File::create("hello.txt").unwrap_or_else(|error| {
                panic!("Problem creating the file: {:?}", error);
            })
        } else {
            panic!("Problem opening the file: {:?}", error);
        }
    });
    write.write_all(b"Hello, world")?;

    // let write_rs = File::open("xyz1.txt");
    // let mut write;
    // if write_rs.is_ok() {
    //     write = write_rs.unwrap();
    //     write.write_all(b"Hello, world")?;
    // }

    Ok(())
}

/// 使用 OpenOptions 灵活读写
fn open_with_option() {
    use std::fs::OpenOptions;
    let mut write = OpenOptions::new().read(true)
                                     .append(true)
                                     .create(true)
                                     .open("foo.txt").unwrap();

    write.write_all(b"hahhaahah").unwrap_or_else(|error| {
        println!("{}", error)
    });

    let mut read = OpenOptions::new().read(true)
                                     .open("foo.txt")
                                     .unwrap();

    let mut contents = String::new();
    read.read_to_string(&mut contents).unwrap();
    println!("contents: {}", contents);
}