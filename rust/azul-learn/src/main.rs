extern crate azul;

use azul::{prelude::*, widgets::{label::Label, button::Button}};

struct DataModel {
  counter: usize,
}

impl Layout for DataModel {
    fn layout(&self, _info: LayoutInfo<Self>) -> Dom<Self> {
        let label = Label::new(format!("{}", self.counter)).dom();
        let button = Button::with_label("Update counter").dom()
            .with_callback(On::MouseUp, |cb_info: CallbackInfo<Self>| { cb_info.state.data.counter += 1; Redraw });

        let dom = Dom::div()
            .with_child(label)
            .with_child(button);

        println!("dom:\r\n{}", dom.debug_dump());
        dom
    }
}

fn main() {
    let mut app = App::new(DataModel { counter: 0 }, AppConfig::default()).unwrap();
    let window = app.create_window(WindowCreateOptions::default(), css::native()).unwrap();
    app.run(window).unwrap();
}