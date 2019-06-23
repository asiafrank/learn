fn main() {
    println!("Hello, world!");

    let r;
    {
        let x = 5;
        r = x;
    }

    println!("r: {}", r);
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        assert_eq!(2 + 2, 4);
    }
}