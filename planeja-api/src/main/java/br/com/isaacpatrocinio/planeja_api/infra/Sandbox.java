package br.com.isaacpatrocinio.planeja_api.infra;

import br.com.isaacpatrocinio.planeja_api.dominio.cartao.CartaoRepository;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.BandeiraCartao;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.CartaoEntity;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.CategoriaRepository;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.model.CategoriaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Sandbox implements CommandLineRunner {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void salvarCartao() {
        if (cartaoRepository.count() == 0) {
            CartaoEntity cartao = new CartaoEntity();
            cartao.setNome("Picpay Platinum");
            cartao.setBandeira(BandeiraCartao.MASTERCARD);
            cartaoRepository.save(cartao);
        }
    }

    public void salvarCategoria() {
        if (categoriaRepository.count() == 0) {
            CategoriaEntity c1 = new CategoriaEntity();
            c1.setNome("Lazer");
            c1.setAtivo(true);

            CategoriaEntity c2 = new CategoriaEntity();
            c2.setNome("Alimentação");
            c2.setAtivo(true);

            CategoriaEntity c3 = new CategoriaEntity();
            c3.setNome("Saúde");
            c3.setAtivo(true);

            var mockList = Arrays.asList(c1, c2, c3);
            categoriaRepository.saveAll(mockList);
        }
    }

    @Override
    public void run(String... args) throws Exception {
//      salvarCartao();
        salvarCategoria();
    }
}