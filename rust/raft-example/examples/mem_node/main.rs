use std::{sync::mpsc::{channel, RecvTimeoutError}, time::{Instant, Duration}};
use raft::{
    Config,
    storage::MemStorage,
    raw_node::RawNode,
};
use std::collections::HashMap;

enum Msg {
    Propose {
        id: u8,
        callback: Box<Fn() + Send>,
    },
    Raft(Message),
}

fn main() {
    // Select some defaults, then change what we need.
    let config = Config {
        id: 1,
        peers: vec![1],
        ..Default::default()
    };
    let storage = MemStorage::default();
    // ... Make any configuration changes.
    // After, make sure it's valid!
    config.validate().unwrap();
    // We'll use the built-in `MemStorage`, but you will likely want your own.
    // Finally, create our Raft node!
    let mut node = RawNode::new(&config, storage, vec![]).unwrap();
    // We will coax it into being the lead of a single node cluster for exploration.
    node.raft.become_candidate();
    node.raft.become_leader();

    //------ Ticking the Raft node ------

    // We're using a channel, but this could be any stream of events.
    let (tx, rx) = channel();
    let timeout = Duration::from_millis(100);
    let mut remaining_timeout = timeout;

    // Send the `tx` somewhere else...
    // Simulate a message coming down the stream.
    tx.send(Msg::Propose { id: 1, callback: Box::new(|| ()) });

    let mut cbs = HashMap::new();

    loop {
        let now = Instant::now();

        match rx.recv_timeout(remaining_timeout) {
            Ok(Msg::Propose { id, callback }) => {
                cbs.insert(id, callback);
                node.propose(vec![], vec![id]).unwrap();
            }
            Ok(Msg::Raft(m)) => node.step(m).unwrap(),
            Err(RecvTimeoutError::Timeout) => (),
            Err(RecvTimeoutError::Disconnected) => unimplemented!(),
        }

        let elapsed = now.elapsed();
        if elapsed >= remaining_timeout {
            remaining_timeout = timeout;
            // We drive Raft every 100ms.
            node.tick();
        } else {
            remaining_timeout -= elapsed;
        }
    }
}

