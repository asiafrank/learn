use std::mem::transmute;

/// 字节码实验
pub fn byte_sample() {
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