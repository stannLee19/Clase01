/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Entidades.Profesor;
import com.unab.edu.conexionmysql.ConexionBd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanly
 */
public class ClsProfesor {

    ConexionBd claseconectar = new ConexionBd();
    Connection conectar = claseconectar.retornarConexion();

    public boolean LoguinProfesor(String UsuaioProfe, String PassProfe) {
        ArrayList<Profesor> ListarUsuariosPro = new ArrayList<>();
        ArrayList<Profesor> ListaContraPro = new ArrayList<>();
        try {
            CallableStatement st = conectar.prepareCall("call SP_S_LoguinProfesor(?,?)");
            st.setString("pusuariop", UsuaioProfe);
            st.setString("pPassp", PassProfe);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Profesor prof = new Profesor();
                prof.setUsuariop(rs.getNString("Usuariop"));
                prof.setPassp(rs.getNString("Passp"));
                ListarUsuariosPro.add(prof);
            }
            String usuariodebasedatos = null;
            String passdebasededatos = null;
            for (var iterador : ListarUsuariosPro) {
                usuariodebasedatos = iterador.getUsuariop();
                passdebasededatos = iterador.getPassp();
            }
            
            //JOptionPane.showMessageDialog(null, usuariodebasedatos+" y "+passdebasededatos);
            //Contraseña -----------------------------------------------------------------------------------

            CallableStatement st2 = conectar.prepareCall("call Sp_CrirpProfesor(?)");
            st2.setString("PcripPassP", PassProfe);
            ResultSet rs2 = st2.executeQuery();
            while (rs2.next()) {
                Profesor escrip = new Profesor();

                escrip.setPassp(rs2.getNString("Crip"));
                //JOptionPane.showMessageDialog(null, escrip.getPassp()+ " Aqui Estamos");
                ListaContraPro.add(escrip);
            }

            String passcrip = null;
            for (var iterador : ListaContraPro) {

                passcrip = iterador.getPassp();

                PassProfe = passcrip;

            }

            if (usuariodebasedatos != null && passdebasededatos != null) {
                if (usuariodebasedatos.equals(UsuaioProfe) && passdebasededatos.equals(PassProfe)) {
                    return true;
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al acceder" + e);
        }
        return false;

    }

}
