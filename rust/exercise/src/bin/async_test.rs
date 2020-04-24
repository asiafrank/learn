
use futures::executor::{block_on, ThreadPool};
use std::thread::sleep;
use std::time::Duration;
use futures_timer::Delay;

async fn learn_song() {
    println!("learn song");
}

async fn sing_song() {
    println!("sing song");
}

async fn dance() {
    println!("dance");
}

async fn learn_and_sing() {
    learn_song().await;
    sing_song().await;
}

async fn async_main() {
    let f1 = learn_and_sing();
    let f2 = dance();
    futures::join!(f1, f2);
}

async fn loop_interval(times: u32) {
    let mut i = 0;
    while i < times {
        Delay::new(Duration::from_secs(1)).await;
        println!("interval {}", i);
        i = i + 1;
    }
}

// 异步例子
fn main() {
    let pool = ThreadPool::new().unwrap();
    {
        println!("--------------thread pool spawn----------");
        pool.spawn_ok(async_main());
        sleep(Duration::from_secs(1));
    }

    {
        println!("--------------loop interval----------");
        pool.spawn_ok(loop_interval(4));
        sleep(Duration::from_secs(5));
    }

    {
        println!("--------------block_on------------");
        block_on(async_main());
    }
}