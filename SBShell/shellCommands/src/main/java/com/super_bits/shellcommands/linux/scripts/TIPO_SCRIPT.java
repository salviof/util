/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.linux.scripts;

/**
 *
 *
 * @author sfurbino
 */
public enum TIPO_SCRIPT {

    PERSONALIZADO {

                @Override
                public Script getStript() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            },
    BACKUP {

                @Override
                public Script getStript() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            },
    CRIARPROJETO {

                @Override
                public Script getStript() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            },
    CONFIGURAR_FIREWALL {

                @Override
                public Script getStript() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };

    public abstract Script getStript();
}
