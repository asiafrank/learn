fn main() {
    windows::build!(
        windows::data::xml::dom::*
        windows::win32::system_services::{CreateEventW, SetEvent, WaitForSingleObject}
        windows::win32::windows_programming::CloseHandle
    );
}