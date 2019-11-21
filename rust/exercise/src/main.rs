mod variable;
mod slice;
mod struct_object;
mod collections;
mod unsafe_sample;
mod linked_list;
mod list;

use std::mem::transmute;

fn main() {
    variable::mutable_variable();
    slice::slice();
    struct_object::struct_object();
    collections::vector_example();
    collections::vector_multi_type();
    unsafe_sample::unsafe_sample();
    linked_list::linked_list_sample();

    let key_size: usize = 55535;
    let b = unsafe {
        transmute::<usize, [u8; 8]>(key_size)
    };
    // little endian
    println!("{}", b[0]); // 低位
    println!("{}", b[1]);
    println!("{}", b[2]);
    println!("{}", b[3]);
    println!("{}", b[4]);
    println!("{}", b[5]);
    println!("{}", b[6]);
    println!("{}", b[7]); // 高位
}