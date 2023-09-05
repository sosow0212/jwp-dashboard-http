package nextstep.jwp.controller.login;

import javassist.NotFoundException;
import nextstep.jwp.controller.base.AbstractController;
import nextstep.jwp.exception.BadRequestException;
import nextstep.jwp.exception.UnAuthorizedException;
import nextstep.jwp.model.User;
import nextstep.jwp.service.UserService;
import org.apache.catalina.session.Session;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.Status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class LoginController extends AbstractController {

    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(final HttpRequest httpRequest) throws Exception {
        return super.handle(httpRequest);
    }

    @Override
    protected HttpResponse doGet(final HttpRequest httpRequest) throws Exception {
        if (httpRequest.getSessionAttribute("user").isPresent()) {
            return HttpResponse.withResource(Status.FOUND, "/index.html");
        }

        return HttpResponse.okWithResource("/login.html");
    }

    @Override
    protected HttpResponse doPost(final HttpRequest httpRequest) throws Exception {
        Map<String, String> params = httpRequest.getBody().getParams();
        User user;

        try {
            user = userService.login(params.get("account"), params.get("password"));
        } catch (BadRequestException e) {
            return HttpResponse.withResource(Status.BAD_REQUEST, "/index.html");
        } catch (UnAuthorizedException e) {
            return HttpResponse.withResource(Status.UNAUTHORIZED, "/401.html");
        }

        return getHttpResponseWithCookie(httpRequest, user);
    }

    private static HttpResponse getHttpResponseWithCookie(final HttpRequest httpRequest, final User user) throws IOException, URISyntaxException, NotFoundException {
        Session session = httpRequest.createSession();
        session.setAttribute("user", user);
        HttpResponse response = HttpResponse.withResource(Status.FOUND, "/index.html");
        response.setCookie(Cookie.fromUserJSession(session.getId()));
        return response;
    }

}
