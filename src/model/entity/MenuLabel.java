package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuLabel {
    private JLabel nameLabel;
    private JTextField priceText;
    private JTextField statusText;
}
