use chrono::{DateTime, Local};

fn main() {
    let local: DateTime<Local> = Local::now();
    println!("Hello world!, {}", local);
}