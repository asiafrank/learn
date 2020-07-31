use std::error::Error;
use std::fs::{File, OpenOptions};

use serde::{Deserialize, Serialize};
use serde::export::fmt::Debug;
use serde::de::DeserializeOwned;
use std::collections::{HashSet, HashMap};

/// 已支付订单
#[derive(Debug, Deserialize, Serialize, Clone)]
struct PayedOrder {
    #[serde(rename = "id")]
    order_id: u32,
    #[serde(rename = "productName")]
    product_name: String,
    #[serde(rename = "orderNo")]
    order_no: String,
    #[serde(rename = "userId")]
    user_id: u32,
    #[serde(rename = "createTime")]
    create_time: String,
    #[serde(rename = "modifyTime")]
    modify_time: String,
    #[serde(rename = "payTime")]
    pay_time: String,
    #[serde(rename = "sourceId")]
    source_id: Option<u32>,
    #[serde(rename = "sourceType")]
    source_type: Option<u32>,
    #[serde(rename = "sourceIdStr")]
    source_id_str: String,
}

/// 筛选前/后订单
#[derive(Debug, Deserialize, Serialize, Clone)]
struct FilteredOrder {
    #[serde(rename = "orderid")]
    order_id: u32,
    #[serde(rename = "userid")]
    user_id: u32,
    #[serde(rename = "paytime")]
    pay_time: String,
    #[serde(rename = "triggertime")]
    trigger_time: String,
    #[serde(rename = "name")]
    log_name: String,
    #[serde(rename = "device")]
    device: String,
    #[serde(rename = "collecttype")]
    collect_type: u32,
}

fn read_csv<T: DeserializeOwned + Debug>(f: File) -> Result<Vec<T>, Box<dyn Error>> {
    let mut list: Vec<T> = Vec::new();
    let mut rdr = csv::Reader::from_reader(f);
    for result in rdr.deserialize() {
        let record: T = result?;
        list.push(record);
    }
    Ok(list)
}

fn write_csv<T: Serialize>(f: File, list: Vec<T>) -> Result<(), Box<dyn Error>> {
    let mut wtr = csv::Writer::from_writer(f);

    // When writing records with Serde using structs, the header row is written
    // automatically.
    for e in list {
        wtr.serialize(e)?;
    }
    wtr.flush()?;
    Ok(())
}

// 读取 csv 文件
fn main() -> Result<(), Box<dyn Error>> {
    let base_dir = "/Users/asiafrank/Downloads/20200526订单分析/";
    let payed_order_file_path = format!("{}20200526已支付订单.csv", base_dir);
    let filter_before_order_file_path = format!("{}20200526筛选前的订单（无120分钟过滤）.csv", base_dir);
    let filter_after_order_file_path = format!("{}20200526筛选后的订单（无120分钟过滤）.csv", base_dir);

    let payed_order_file = File::open(payed_order_file_path)?;
    let filter_before_order_file = File::open(filter_before_order_file_path)?;
    let filter_after_order_file = File::open(filter_after_order_file_path)?;

    let payed_order_list = read_csv::<PayedOrder>(payed_order_file).unwrap();
    let filter_before_order_list = read_csv::<FilteredOrder>(filter_before_order_file).unwrap();
    let filter_after_order_list = read_csv::<FilteredOrder>(filter_after_order_file).unwrap();

    // 已支付订单(a)
    let mut a_set: HashSet<u32> = HashSet::new();
    let mut a_map: HashMap<u32, PayedOrder> = HashMap::new();
    for e in &payed_order_list {
        a_set.insert(e.order_id);
        a_map.insert(e.order_id, e.clone());
    }

    // 筛选前订单(b)
    let mut b_set: HashSet<u32> = HashSet::new();
    let mut b_map: HashMap<u32, FilteredOrder> = HashMap::new();
    for e in &filter_before_order_list {
        b_set.insert(e.order_id);
        b_map.insert(e.order_id, e.clone());
    }

    // 筛选后订单(c)
    let mut c_set: HashSet<u32> = HashSet::new();
    let mut c_map: HashMap<u32, FilteredOrder> = HashMap::new();
    for e in &filter_after_order_list {
        c_set.insert(e.order_id);
        c_map.insert(e.order_id, e.clone());
    }

    // 已支付订单(a) 与 筛选前的差(b)
    let mut a_b_list: Vec<PayedOrder> = Vec::new();
    for o in &payed_order_list {
        let opt = b_map.get(&o.order_id);
        match opt {
            None => {
                a_b_list.push(o.clone());
            }
            _ => {}
        }
    }

    let a_b_file_path = format!("{}已支付订单与筛选前的差（无120分钟过滤）.csv", base_dir);
    let a_b_file = OpenOptions::new().read(true)
        .append(true)
        .create(true)
        .open(a_b_file_path).unwrap();
    write_csv::<PayedOrder>(a_b_file, a_b_list)?;

    // 筛选前(b)与筛选后(c)的差
    let mut b_c_list: Vec<FilteredOrder> = Vec::new();
    for o in &filter_before_order_list {
        let opt = c_map.get(&o.order_id);
        match opt {
            None => {
                b_c_list.push(o.clone());
            }
            _ => {}
        }
    }
    let b_c_file_path = format!("{}筛选前与筛选后的差（无120分钟过滤）.csv", base_dir);
    let b_c_file = OpenOptions::new().read(true)
        .append(true)
        .create(true)
        .open(b_c_file_path)?;
    write_csv::<FilteredOrder>(b_c_file, b_c_list)?;
    Ok(())
}