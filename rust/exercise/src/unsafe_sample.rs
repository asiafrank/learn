// 几个基本的 unsafe 用法
// 1.解引用裸指针
// 2.调用不安全的函数或方法
// 3.访问或修改可变静态变量
// 4.实现不安全 trait
pub fn unsafe_sample() {
    dereference_pointer();
    invoke_unsafe_function();
    safe_abstraction();
    invoke_c_lib();
    access_and_change_static();
}

// 1.解引用裸指针
fn dereference_pointer() {
    let mut num = 5;

    let r1 = &num as *const i32;
    let r2 = &mut num as *mut i32;
    unsafe {
        *r2 = 2;
        println!("r1 is: {}", *r1);
        println!("r2 is: {}", *r2);
    }
}

// 2.调用不安全函数或方法
unsafe fn dangerous() {}
fn invoke_unsafe_function() {
    unsafe {
        dangerous();
    }
}

// 2.1 创建不安全代码的安全抽象
fn safe_abstraction() {
    let mut v = vec![1, 2, 3, 4, 5, 6];
    let r = &mut v[..];
    let (a, b) = r.split_at_mut(3); // split_at_mut 该方法就是一个安全抽象的例子

    assert_eq!(a, &mut [1, 2, 3]);
    assert_eq!(b, &mut [4, 5, 6]);
}

// 2.2 使用 extern 函数调用外部代码
extern "C" {
    fn abs(input: i32) -> i32;
}

fn invoke_c_lib() {
    unsafe {
        println!("Absolute value of -3 according to C: {}", abs(-3));
    }
}

// 3. 访问或修改可变静态变量
static mut COUNTER: u32 = 0;

fn add_to_count(inc: u32) {
    unsafe {
        COUNTER += inc;
    }
}

fn access_and_change_static() {
    add_to_count(3);
    unsafe {
        println!("COUNTER: {}", COUNTER);
    }
}

// 4. 实现不安全的 trait
unsafe trait Foo {
    // methods go here
}

unsafe impl Foo for i32 {
    // method implementations go here
}