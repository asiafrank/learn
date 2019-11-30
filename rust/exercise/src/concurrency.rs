// 并发例子
// https://kaisery.github.io/trpl-zh-cn/ch16-01-threads.html
// https://rust-lang-nursery.github.io/rust-cookbook/concurrency.html

use std::thread;
use std::time::Duration;

pub fn concurrency_example() {
    thread::spawn(|| {
       for i in 1..10 {
           println!("hi number {} from the spawned thread!", i);
           thread::sleep(Duration::from_millis(1));
       }
    });

    for i in 1..5 {
        println!("hi number {} from the main thread!", i);
        thread::sleep(Duration::from_millis(1))
    }
}