use std::fs::{File};
use std::io::prelude::*;
use std::io::{Error, ErrorKind};
use std::thread;
use std::time::Duration;
use std::thread::JoinHandle;

/// 文件读写例子

pub fn file_sample() {
//    file_write_1().unwrap();
//    open_with_option();
//    read_lines();
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
/// TODO: 尝试并发读写都用同一个 file 实例
fn open_with_option() {
    use std::fs::OpenOptions;
    let mut handles = Vec::new();

    for i in 1..3 {
        let handle = thread::spawn(move || {
            let id = i;
            println!("write_s {}", id);

            let mut buf = format!("thread={}\n", id);
            let mut write = OpenOptions::new().read(true)
                                              .append(true)
                                              .create(true)
                                              .open("foo.txt").unwrap();

            thread::sleep(Duration::from_millis(100));

            write.write_all(buf.as_ref()).unwrap_or_else(|error| {
                println!("{}", error)
            });
            println!("write {}", id);
            thread::sleep(Duration::from_millis(100));
        });
        handles.push(handle);
    }

    for i in 1..100 {
        let handle = thread::spawn(move || {
            let mut read = OpenOptions::new().read(true)
                                             .open("foo.txt")
                                             .unwrap();

            let mut contents = String::new();
            read.read_to_string(&mut contents).unwrap();
            thread::sleep(Duration::from_millis(1));
            println!("read {}", i);

            println!("contents: {}", contents);
        });
        handles.push(handle);
    }

    for h in handles {
        h.join().unwrap();
    }
}

/// 使用 BufReader 读文件
fn read_lines() {
    use std::io::BufReader;

    let file = File::open("foo.txt").unwrap();
    let mut reader = BufReader::new(file);
    let mut buf = String::new();
    let mut line_number = 0;
    loop {
        let bytes_num = reader.read_line(&mut buf).unwrap();
        println!("bytes_num={}", bytes_num);
        if bytes_num == 0 { // EOF
            break
        }
        print!("{}: {}", line_number, buf);
        buf.clear(); // read_line 是对 String 的追加操作，因此这里需要 clear
        line_number = line_number + 1;
    }
}