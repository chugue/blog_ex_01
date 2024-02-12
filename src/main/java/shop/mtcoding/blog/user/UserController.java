package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;


    @PostMapping("/join")
    public String join (UserRequest.JoinDTO requestDTO, HttpServletRequest request){
        if (requestDTO.getUsername().length() < 3){
            request.setAttribute("msg", "username은 3자 이상이어야 합니다.");
            request.setAttribute("status", 400);
            return "error/40x";
        }
        if (requestDTO.getPassword().length() > 20){
            request.setAttribute("msg", "password는 20자를 넘길 수 없습니다.");
            request.setAttribute("status", 400);
            return "error/40x";
        }

        userRepository.save(requestDTO);
        return "user/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO, HttpServletRequest request) {
        User user = userRepository.findByUsernameAndPassword(requestDTO);
        if (user == null ){
            request.setAttribute("msg", "사용자를 찾을 수 없습니다.");
            request.setAttribute("status", 404);
            return "error/40x";
        }
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
