package br.com.isaacpatrocinio.planeja_api.infra;

import br.com.isaacpatrocinio.planeja_api.dominio.cartao.CartaoRepository;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.BandeiraCartao;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sandbox implements CommandLineRunner {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void salvarCartao() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNome("Picpay Platinum");
        cartao.setBandeira(BandeiraCartao.MASTERCARD);
        cartaoRepository.save(cartao);
    }

    @Override
    public void run(String... args) throws Exception {
//        salvarCartao();
    }
}