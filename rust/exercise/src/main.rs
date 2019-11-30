extern crate ssh2;

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
}