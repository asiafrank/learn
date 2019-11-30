use std::mem::transmute;

/// 字节码实验
pub fn byte_sample() {
    let num: usize = 55535;
    let b = u64_to_bytes(num as u64);
    // little endian
    println!("{}", b[0]); // 低位
    println!("{}", b[1]);
    println!("{}", b[2]);
    println!("{}", b[3]);
    println!("{}", b[4]);
    println!("{}", b[5]);
    println!("{}", b[6]);
    println!("{}", b[7]); // 高位

    let num0 = bytes_to_u64(&b);
    println!("....{}", num0);
}

/// 64 bit 数，转换为 8 元素数组
fn u64_to_bytes(num: u64) -> [u8; 8] {
    let b = unsafe {
        transmute::<u64, [u8; 8]>(num)
    };
    b
}

/// 8 个元素的 byte 数组，转换为 u64
fn bytes_to_u64(buf: &[u8]) -> u64 {
    let mut x : u64 = 0;
    x = x | buf[7] as u64;
    x = (x << 8) | buf[6] as u64; // 高位
    x = (x << 8) | buf[5] as u64;
    x = (x << 8) | buf[4] as u64;
    x = (x << 8) | buf[3] as u64;
    x = (x << 8) | buf[2] as u64;
    x = (x << 8) | buf[1] as u64;
    x = (x << 8) | buf[0] as u64; // 低位
    x
}