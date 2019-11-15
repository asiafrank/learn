mod variable;
mod slice;
mod struct_object;
mod collections;
mod unsafe_sample;
mod linked_list;
mod list;

fn main() {
    variable::mutable_variable();
    slice::slice();
    struct_object::struct_object();
    collections::vector_example();
    collections::vector_multi_type();
    unsafe_sample::unsafe_sample();
    linked_list::linked_list_sample();
}