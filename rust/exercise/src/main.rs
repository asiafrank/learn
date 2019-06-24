mod variable;
mod slice;
mod struct_object;
mod collections;

fn main() {
    variable::mutable_variable();
    slice::slice();
    struct_object::struct_object();
    collections::vector_example();
    collections::vector_multi_type();
}