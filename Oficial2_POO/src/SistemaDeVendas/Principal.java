package SistemaDeVendas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Principal() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.windowBorder);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Estabelece a conexão com o banco de dados ao iniciar a janela Principal
        Conexao.conectar();

        JButton BotaoVendas = new JButton("Vendas");
        BotaoVendas.setFont(new Font("Tahoma", Font.PLAIN, 13));
        BotaoVendas.setBackground(SystemColor.menu);
        BotaoVendas.setForeground(new Color(0, 128, 255));
        BotaoVendas.setBounds(270, 85, 88, 44);
        BotaoVendas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaDeVendas telaDeVendas = new TelaDeVendas();
                telaDeVendas.setVisible(true);
            }
        });

        JButton BotaoCliente = new JButton("Cliente");
        BotaoCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
        BotaoCliente.setForeground(SystemColor.textHighlight);
        BotaoCliente.setBackground(SystemColor.menu);
        BotaoCliente.setBounds(74, 85, 88, 44);
        BotaoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setVisible(true);
            }
        });

        JButton BotaoProduto = new JButton("Produtos");
        BotaoProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
        BotaoProduto.setBackground(SystemColor.menu);
        BotaoProduto.setForeground(new Color(0, 128, 255));
        BotaoProduto.setBounds(172, 85, 88, 44);
        BotaoProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProdutoJ produtoJ = new ProdutoJ();
                produtoJ.setVisible(true);
            }
        });

        contentPane.add(BotaoCliente);
        contentPane.add(BotaoProduto);
        contentPane.add(BotaoVendas);
    }
}