package MYSQL;

import java.awt.GridBagConstraints;
import java.awt.Insets;

class MyGridBagConstraints extends GridBagConstraints {
    //自定义GridBagConstraints类
    public MyGridBagConstraints(int x, int y, int w, int h) {
        this.gridx = x;
        this.gridy = y;
        this.gridwidth = w;
        this.gridheight = h;
    }

    MyGridBagConstraints setFill(int fill) {
        this.fill = fill;
        return this;
    }

    MyGridBagConstraints setWeight(int x, int y) {
        this.weightx = x;
        this.weighty = y;
        return this;
    }

    MyGridBagConstraints setIpad(int x, int y) {
        this.ipadx = x;
        this.ipady = y;
        return this;
    }

    MyGridBagConstraints setInset(Insets inset) {
        this.insets = inset;
        return this;
    }
}
