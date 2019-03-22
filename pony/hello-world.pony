use "http"
use "time"

actor Main
  new create(env: Env) =>
    let service = try env.args(1)? else "8080" end
    let limit = try env.args(2)?.usize()? else 100 end
    let host = "localhost"
    let logger = CommonLog(env.out)
    let auth = try
    env.root as AmbientAuth
  else
    env.out.print("unable to use network")
    return
  end

  HTTPServer(
  auth,
  ListenHandler(env),
  BackendMaker.create(env),
  logger
  where service=service, host=host, limit=limit, reversedns=auth)

class ListenHandler
  let _env: Env

  new iso create(env: Env) =>
    _env = env

  fun ref listening(server: HTTPServer ref) =>
    try
      (let host, let service) = server.local_address().name()?
      _env.out.print("listening: " + host)
    else
      _env.out.print("Couldn't get local address.")
      server.dispose()
    end

  fun ref not_listening(server: HTTPServer ref) =>
    _env.out.print("Failed to listen.")

  fun ref closed(server: HTTPServer ref) =>
    _env.out.print("Shutdown.")

class BackendMaker is HandlerFactory
  let _env: Env

  new val create(env: Env) =>
    _env = env

  fun apply(session: HTTPSession): HTTPHandler^ =>
    BackendHandler.create(_env, session)

class BackendHandler is HTTPHandler
  let _env: Env
  let _session: HTTPSession
  var _response: Payload = Payload.response()

  new ref create(env: Env, session: HTTPSession) =>
    _env = env
    _session = session

  fun ref apply(request: Payload val) =>
    _response.add_chunk(getResponse(request))
    try
      (let s, let ns) = Time.now()
      let timestamp = PosixDate.create(s, ns).format("%a, %d %b %Y %T %Z")?
      _response.headers().insert("Date", timestamp)?
    else
      None
    end

    _session(_response = Payload.response())

  fun getResponse(request: Payload val): String =>
    let name = try
      let split = request.url.query.split_by("=")
      split(1)?
    else
      "World"
    end
    "{\"message\":\"Hello " + name + "!\"}"

  fun ref chunk(data: ByteSeq val) =>
    _response.add_chunk("\n")
    _response.add_chunk(data)

  fun ref finished() =>
    _env.out.print("Finished")
    _session(_response = Payload.response())
