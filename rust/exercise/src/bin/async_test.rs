
use futures::executor::block_on;

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

// 异步例子
fn main() {
    block_on(async_main());
}