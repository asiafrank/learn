fn main() {
    mutable_variable();
    slice();
    struct_object();
}

fn mutable_variable() {
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

fn slice() {
    let s = String::from("Hello World!");
    let word = first_word(&s);
    println!("the first word is: {}", word);
}

fn first_word(s: &String) -> &str {
    let bytes = s.as_bytes();

    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[0..i]
        }
    }

    &s[..]
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32
}

impl Rectangle {
    fn are(&self) -> u32 {
        self.width * self.height
    }

    fn can_hold(&self, other: &Rectangle) -> bool {
        self.width > other.width && self.height > other.height
    }

    fn square(size: u32) -> Rectangle {
        Rectangle{width: size, height: size}
    }
}

fn struct_object() {
    let rect1 = Rectangle{width: 30, height: 50};
    let rect2 = Rectangle{width: 10, height: 40};
    let rect3 = Rectangle{width: 60, height: 45};

    println!("The are of the rectangle is {} square pixels.", rect1.are());
    println!("Can rect1 hold rect2? {}", rect1.can_hold(&rect2));
    println!("Can rect1 hold rect3? {}", rect1.can_hold(&rect3));
}