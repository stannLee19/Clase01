/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Entidades.Estudiante;
import com.unab.edu.Entidades.Persona;

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
        ArrayList<Estudiante> ListaUsuarios = new ArrayList<>();
        ArrayList<Estudiante> ListarContra = new ArrayList<>();
        try {
            CallableStatement st = conectar.prepareCall("call SP_S_LoguinEstudiante(?,?)");

            st.setString("pusuario", usuario);
            st.setString("ppass", Pass);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Estudiante es = new Estudiante();
                es.setUsu(rs.getNString("USU"));
                es.setPass(rs.getNString("PASS"));
                ListaUsuarios.add(es);
            }
            String usuariodebasedatos = null;
            String passdebasededatos = null;
            for (var iterador : ListaUsuarios) {
                usuariodebasedatos = iterador.getUsu();
                passdebasededatos = iterador.getPass();

            }

            CallableStatement st2 = conectar.prepareCall("call Sp_s_Crip(?)");
            st2.setString("PcripPass", Pass);
            ResultSet rs2 = st2.executeQuery();
            while (rs2.next()) {
                Estudiante escrip = new Estudiante();

                escrip.setPass(rs2.getNString("crip"));
                ListarContra.add(escrip);
            }

            String passcrip = null;
            for (var iterador : ListarContra) {

                passcrip = iterador.getPass();

                Pass = passcrip;

            }
           
            
            if(usuariodebasedatos!=null &&passdebasededatos!=null ){
            if (usuariodebasedatos.equals(usuario) && passdebasededatos.equals(Pass)) {
                return true;
            }
            }
            conectar.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
        return false;
    }

    // Crud de estudiante ------------inicia aqui------------
    public ArrayList<Estudiante> MostrarEstudiante() {
        ArrayList<Estudiante> Estudiantes = new ArrayList<>();

        try {
            CallableStatement Statement = conectar.prepareCall("call SP_S_JOINMOSTRARESTUDIANTE()");
            ResultSet RS = Statement.executeQuery();
            while (RS.next()) {
                Estudiante est = new Estudiante();
                est.setId(RS.getInt("idestudiante"));
                est.setMatricula(RS.getInt("matricula"));
                est.setIdPersona(RS.getInt("idPersona"));
                est.setNombre(RS.getString("Nombre"));
                est.setUsu(RS.getString("USU"));
                est.setPass(RS.getString("PASS"));
                est.setNIE(RS.getString("NIE"));

                Estudiantes.add(est);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Estudiantes;

    }

    public void GuardarEstudiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_I_ESTUDIANTE (?,?,?,?,?);");

            Statement.setInt("Pmatricula", Estu.getMatricula());
            Statement.setInt("PidPersona", Estu.getIdPersona());
            Statement.setString("PUSU", Estu.getUsu());
            Statement.setString("pPASS", Estu.getPass());
            Statement.setString("pNIE", Estu.getNIE());
            Statement.execute();
            JOptionPane.showMessageDialog(null, " Estudiante guardado con exito");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void BorrarEstudiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_D_ESTUDIANTE(?)");
            Statement.setInt("PIDestduaintes", Estu.getId());
            Statement.execute();
            JOptionPane.showMessageDialog(null, "Estudiante Eliminado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void ActualizarEstuiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_U_ESTUDIANTE(?,?,?,?,?,?,?)");
            Statement.setInt("pIDestudiantes", Estu.getId());
            Statement.setInt("pMatricula", Estu.getMatricula());
            Statement.setInt("pIDpersonas", Estu.getIdPersona());
            Statement.setString("pUSUARIO", Estu.getUsu());
            Statement.setString("pPassword", Estu.getPass());
            Statement.setString("pNIE", Estu.getNIE());
            Statement.execute();

            JOptionPane.showMessageDialog(null, " Estudiante Actualizado Correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
