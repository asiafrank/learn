mod bindings {
    ::windows::include_bindings!();
}

use bindings::{
    windows::data::xml::dom::*,
    windows::win32::system_services::{CreateEventW, SetEvent, WaitForSingleObject},
    windows::win32::windows_programming::CloseHandle,
};

fn main() -> windows::Result<()> {
    let doc = XmlDocument::new()?;
    doc.load_xml("<html>hello world</html>")?;

    let root = doc.document_element()?;
    assert!(root.node_name()? == "html");
    assert!(root.inner_text()? == "hello world");

    unsafe {
        let event = CreateEventW(
            std::ptr::null_mut(),
            true.into(),
            false.into(),
            std::ptr::null(),
        );

        SetEvent(event).ok()?;
        WaitForSingleObject(event, 0);
        CloseHandle(event).ok()?;
    }

    Ok(())
}