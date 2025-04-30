import java.awt.*;
import java.io.*; 
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CaesarCipherGUI extends JFrame { 

    private static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"; 
    private static final String ALPHABET_ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private final JTextArea inputTextArea; 
    private final JTextArea outputTextArea; 
    private final JTextField keyTextField; 

    //Структура GUI 
    public CaesarCipherGUI() { 
        super("Шифр Цезаря");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false); 
        setLayout(new BorderLayout());
        inputTextArea = new JTextArea(); 
        outputTextArea = new JTextArea(); 
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea); 
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea); 
        
        JPanel keyPanel = new JPanel(); 
        JLabel keyLabel = new JLabel("Ключ (сдвиг):"); 
        keyTextField = new JTextField(5); 
        keyPanel.add(keyLabel); 
        keyPanel.add(keyTextField); 

        JPanel buttonPanel = new JPanel(); 
        JButton encryptButton = new JButton("Шифровать"); 
        JButton decryptButton = new JButton("Расшифровать"); 
        JButton openFileButton = new JButton("Открыть файл"); 
        buttonPanel.add(openFileButton); 
        buttonPanel.add(encryptButton); 
        buttonPanel.add(decryptButton);

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.add(inputScrollPane); 
        textPanel.add(outputScrollPane); 
        add(keyPanel, BorderLayout.NORTH); 
        add(textPanel, BorderLayout.CENTER); 
        add(buttonPanel, BorderLayout.SOUTH); 

        openFileButton.addActionListener(e -> openFile()); 
        encryptButton.addActionListener(e -> encryptText());
        decryptButton.addActionListener(e -> decryptText()); 

        setVisible(true); 
    }

    //Реализация открытия файла
    private void openFile() { 
        JFileChooser fileChooser = new JFileChooser(); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовые файлы", "txt"); 
        fileChooser.setFileFilter(filter); 
        int returnVal = fileChooser.showOpenDialog(this); 

        if (returnVal == JFileChooser.APPROVE_OPTION) { 
            File file = fileChooser.getSelectedFile(); 
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 
                StringBuilder sb = new StringBuilder(); 
                String line; 
                while ((line = reader.readLine()) != null) { 
                    sb.append(line).append("\n"); 
                }
                inputTextArea.setText(sb.toString()); 
            } catch (IOException ex) { 
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }

    //Реализация шифрования
    private void encryptText() { 
        try { 
            int key = Integer.parseInt(keyTextField.getText()); 
            validateKey(key); 
            String text = inputTextArea.getText(); 
            String encryptedText = caesarCipher(text, key); 
            outputTextArea.setText(encryptedText); 
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "Введите корректный ключ (число).", "Ошибка", JOptionPane.ERROR_MESSAGE); 
        } catch (IllegalArgumentException e) { 
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE); 
        }
    }

    //Реализация расшифровки
    private void decryptText() { 
        try { 
            int key = Integer.parseInt(keyTextField.getText()); 
            validateKey(key); 
            String text = inputTextArea.getText(); 
            String decryptedText = caesarCipher(text, -key); 
            outputTextArea.setText(decryptedText); 
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "Введите корректный ключ (число).", "Ошибка", JOptionPane.ERROR_MESSAGE); 
        } catch (IllegalArgumentException e) { 
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE); 
        }
    }

    private String caesarCipher(String text, int key) { 
        StringBuilder result = new StringBuilder(); 
        key = (key % ALPHABET.length() + ALPHABET.length()) % ALPHABET.length();

        for (char character : text.toUpperCase().toCharArray()) { 
            if (ALPHABET.indexOf(character) != -1) { 
                int originalPosition = ALPHABET.indexOf(character); 
                int newPosition = (originalPosition + key) % ALPHABET.length(); 
                char newCharacter = ALPHABET.charAt(newPosition); 
                result.append(newCharacter); 
            } else if (ALPHABET_ENG.indexOf(character) != -1){ 
                int originalPosition = ALPHABET_ENG.indexOf(character); 
                int newPosition = (originalPosition + key) % ALPHABET_ENG.length(); 
                char newCharacter = ALPHABET_ENG.charAt(newPosition); 
                result.append(newCharacter); 
            }
            else { 
                result.append(character);
            }
        }
        return result.toString(); 
    }

    private void validateKey(int key) { 
        if (key < 0 || key >= ALPHABET.length()) { 
            throw new IllegalArgumentException("Ключ должен быть в диапазоне от 0 до " + (ALPHABET.length() - 1)); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CaesarCipherGUI::new); 
    }
}
