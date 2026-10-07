/* Copyright (C) 2026 ska_987 — octobre 2026. SPDX-License-Identifier: GPL-3.0-only */
package fr.ska.mesheures;
import java.io.*;import java.util.*;
public final class BackupFiles {
 private final File dir;
 public BackupFiles(File d){dir=d;}
 public synchronized void save(byte[] data)throws IOException{
  if(!dir.isDirectory()&&!dir.mkdirs())throw new IOException("Dossier indisponible");
  File temp=new File(dir,"pending.tmp");try(FileOutputStream out=new FileOutputStream(temp)){out.write(data);out.getFD().sync();}
  File[] previous=list();long stamp=System.currentTimeMillis();if(previous.length>0){try{stamp=Math.max(stamp,Long.parseLong(previous[0].getName().split("-")[1])+1);}catch(Exception e){}}
  File target=new File(dir,"donnees-"+stamp+"-"+UUID.randomUUID().toString().substring(0,8)+".json");
  if(!temp.renameTo(target))throw new IOException("Enregistrement impossible");
  File[] files=list();for(int i=7;i<files.length;i++)files[i].delete();
 }
 public synchronized File[] list(){File[] f=dir.listFiles((d,n)->n.startsWith("donnees-")&&n.endsWith(".json"));if(f==null)return new File[0];Arrays.sort(f,(a,b)->b.getName().compareTo(a.getName()));return f;}
 public static byte[] read(InputStream in,int max)throws IOException{ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] b=new byte[8192];int n;while((n=in.read(b))!=-1){if(out.size()+n>max)throw new IOException("Fichier trop volumineux");out.write(b,0,n);}return out.toByteArray();}
 public synchronized String latest(){for(File f:list()){try(FileInputStream in=new FileInputStream(f)){return new String(read(in,20000000),"UTF-8");}catch(IOException e){}}return "";}
}
