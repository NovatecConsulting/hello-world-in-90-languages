use std::io;
use std::io::prelude::Write;
use std::io::{BufRead, BufReader};
use std::net::{TcpListener, TcpStream};
use std::time::SystemTime;

extern "C" {
    fn ctime(timep: *const u64) -> *const u8;
    fn strlen(s: *const u8) -> usize;
}

fn current_timestamp() -> String {
    unsafe {
        let timestamp_in_seconds = match SystemTime::now().duration_since(SystemTime::UNIX_EPOCH) {
            Ok(duration) => duration.as_secs(),
            Err(e) => panic!("Failed to calculate duration: {}", e),
        };
        let timestamp = ctime(&timestamp_in_seconds);
        String::from_utf8_unchecked(
            std::slice::from_raw_parts(timestamp, strlen(timestamp) - 1).to_vec(),
        )
        .trim()
        .to_string()
    }
}

fn build_response(stream: &TcpStream) -> io::Result<String> {
    let mut req = String::with_capacity(512);
    let mut reader = BufReader::new(stream);
    reader.read_line(&mut req)?;

    println!("Request:\n{}", req);
    let name = match req.split('?').nth(1) {
        Some(q) => q
            .split('=')
            .nth(1)
            .expect("No '=' after '?'")
            .split_whitespace()
            .nth(0)
            .unwrap(),
        None => "World",
    };
    let response_body = format!("{{\"message\": \"Hello {}!\"}}", name);
    let content_length = response_body.len();
    let timestamp = current_timestamp();
    let response = format!(
        "HTTP/1.1 200 OK
Content-Type: application/json
Date: {}
Content-Length: {}

{}",
        timestamp, content_length, response_body
    );
    println!(
        "Response:
{}",
        response
    );

    Ok(response)
}

fn main() -> io::Result<()> {
    let port = 8080;
    println!("Starting server on port {}", port);
    let server = TcpListener::bind(format!("localhost:{}", port))?;

    for stream in server.incoming() {
        match stream {
            Ok(mut stream) => {
                println!("Client connected");
                let response: String = build_response(&stream)?;
                write!(stream, "{}", response)?;
            }
            Err(e) => println!("Failed to read stream: {}", e),
        }
    }
    Ok(())
}
