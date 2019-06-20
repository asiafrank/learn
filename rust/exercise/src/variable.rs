pub fn mutable_variable() {
    let mut s = String::from("Hello World!");
    let len = not_change(&s);
    println!("{}", len);

    change(&mut s);
    println!("{}", s);
    change(&mut s);
    println!("{}", s);
}

fn not_change(x: &String) -> usize {
    x.len()
}

fn change(x: &mut String) {
    x.push_str("Rust is Funny!");
}