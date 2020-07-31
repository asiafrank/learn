extern crate ssh2;
#[macro_use]
extern crate slog;
extern crate slog_term;
extern crate slog_async;
extern crate futures_timer;

extern crate csv;
extern crate serde;
extern crate chrono;

use std::fs;
use std::fs::OpenOptions;
use slog::Drain;

mod variable;
mod slice;
mod struct_object;
mod collections;
mod unsafe_sample;
mod linked_list;
mod list;
mod byte_sample;
mod file_sample;
mod ssh_sample;
mod concurrency;

fn main() {
    variable::mutable_variable();
    slice::slice();
    struct_object::struct_object();
    collections::vector_example();
    collections::vector_multi_type();
    unsafe_sample::unsafe_sample();
    linked_list::linked_list_sample();
    byte_sample::byte_sample();
    file_sample::file_sample();

    let x = 23;
    println!("{}", x);

//    ssh_sample::ssh_example_1();
//    concurrency::concurrency_example();

    let log_path = "target/your_log_file_path.log";
    let file = OpenOptions::new()
        .create(true)
        .write(true)
        .truncate(true)
        .open(log_path)
        .unwrap();

    let decorator = slog_term::PlainDecorator::new(file);
    let drain = slog_term::FullFormat::new(decorator).build().fuse();
    let drain = slog_async::Async::new(drain).build().fuse();

    let _log = slog::Logger::root(drain, o!());

    info!(_log, "formatted: {}", 1; "log-key" => true);

    let mut num: u64 = 12665987562875263;
    let mut x;
    for i in 0..8 {
        x = num >> (8 * (7 - i));
        println!("num{}={}", i, x as u8);
    }
}