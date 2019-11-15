// TODO: 实现 p72 页 最简单的 DB
fn main() {
    let mut num = 5;

    let r1 = &num as *const i32;
    let r2 = &mut num as *mut i32;
    unsafe {
        *r2 = 2;
        println!("r1 is: {}", *r1);
        println!("r2 is: {}", *r2);
    }
}
