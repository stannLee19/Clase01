/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Entidades.Estudiante;

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
public class ClsEstudiante {

    ConexionBd claseconectar = new ConexionBd();
    Connection conectar = claseconectar.retornarConexion();

    public boolean LoguinEstudiante(String usuario, String Pass) {
        ArrayList<Estudiante> ListaUsuariosPass = new ArrayList<>();

        try {
            CallableStatement Statement = conectar.prepareCall("call SP_S_LoguinEstudiante(?,?)");
            Statement.setString("pusuario", usuario);
            Statement.setString("ppass", Pass);

            ResultSet resultadoDeConsulta = Statement.executeQuery();

            while (resultadoDeConsulta.next()) {

                Estudiante es = new Estudiante();
                es.setUsu(resultadoDeConsulta.getString("USU"));
                es.setPass(resultadoDeConsulta.getString("PASS"));
                ListaUsuariosPass.add(es);

            }
            String usuariodebasededatos = null;
            String pasdebasededaros = null;
            for (var iterador : ListaUsuariosPass) {

                usuariodebasededatos = iterador.getUsu();
                pasdebasededaros = iterador.getPass();

            }
            if (usuariodebasededatos.equals(usuario) && pasdebasededaros.equals(Pass)) {
                return true;

            }
            conectar.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

        return false;
    }

}
