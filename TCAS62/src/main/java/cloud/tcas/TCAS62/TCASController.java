/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.tcas.TCAS62;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Student
 */
@RestController
public class TCASController {
    @Autowired
    TCASService tcasService;
    
    @Autowired
    TokenService tokenService;
    
    Logger logger = LoggerFactory.getLogger(TCASController.class);

    @PostMapping("/login")
    public ResponseEntity<TCAS> authenticate(@Valid @RequestBody HashMap<String, String> inputUser,
                                             HttpServletRequest request) {
        checkUsernameAndPassword(inputUser, request);
        try {
            TCAS tcas = tcasService.findByUsernameAndPassword(inputUser.get("username"), inputUser.get("password"));
            String token = tokenService.createToken(tcas);
            logger.info(System.currentTimeMillis() + " | " + request.getRemoteAddr() + " | " + "login to " + inputUser.get("username"));
            tcas.setToken(token);
            return new ResponseEntity<TCAS>(tcas, HttpStatus.OK);
        } catch (HttpClientErrorException | NullPointerException e) {
            logger.warn(System.currentTimeMillis() + " | " + request.getRemoteAddr() + " | " + "userame or password is invalid");
            throw new NotFoundException("Not Found user. incorrect username or password.");
        }
    }
    
    @PostMapping("/confirm/department/{status}")
//    public ResponseEntity<TCAS> confirm(@RequestBody){
//    }
    //เขียนไม่ทันแล้วครับ
}
