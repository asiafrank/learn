pub fn vector_example() {
    let mut v = vec![1, 2, 3];
    v.push(5);
    v.push(6);
    for item in &v {
        println!("vec {}", item);
    }
}

enum SpreadsheetCell {
    Int(i32),
    Float(f64),
    Text(String),
}

pub fn vector_multi_type() {
    let row = vec![
        SpreadsheetCell::Int(3),
        SpreadsheetCell::Text(String::from("blue")),
        SpreadsheetCell::Float(10.12),
    ];

    let opt = row.get(0);
    match opt {
        Some(x) => match2(x),
        None => println!("row[0] is None")
    }
}

fn match2(x: &SpreadsheetCell) {
    match x {
        SpreadsheetCell::Int(1) => println!("enum int 1"),
        SpreadsheetCell::Int(3) => println!("enum int 3"),
        _ => println!("enum others")
    }
}