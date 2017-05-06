import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;

import static javax.swing.GroupLayout.Alignment.CENTER;

class InfoListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AboutDialog ad = new AboutDialog();
        ad.setVisible(true);
    }
}

class AboutDialog extends JDialog {

    public AboutDialog() {
        super();
        initializesAboutDialogInterface();
    }

    private void initializesAboutDialogInterface() {
        setFont(new Font("Serif", Font.BOLD, 13));

        JLabel author = new JLabel("Author: Rafal Sawicki");
        JLabel index = new JLabel("Index: 229015");
        JLabel name = new JLabel("Graphics Editor 1.0");

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        createsLayout(name, author, index, okButton);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("About Aplet");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createsLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup(CENTER)
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addComponent(arg[3])
                .addGap(200)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(30)
                .addComponent(arg[0])
                .addGap(20)
                .addComponent(arg[1])
                .addGap(20)
                .addComponent(arg[2])
                .addGap(20)
                .addComponent(arg[3])
                .addGap(30)
        );
        pack();
    }
}




class DrawingSurface extends JApplet {

    static Shape activatedObject = null;
    static boolean isSthActivated = false;
    ArrayList<Shape> shapes=new ArrayList();
    private ZRectangle zrect;
    private ZEllipse zell;

    public DrawingSurface() {
         initializesInterface();
     }
    public void init() {
        initializesInterface();
    }

    private void initializesInterface() {
        Shape tmp=new ZRectangle(50, 120, 50, 50);
        shapes.add(tmp);
        tmp=new ZEllipse(150, 250, 80, 80);
        shapes.add(tmp);
    }

    public void paint(Graphics g) {doDrawing(g);}

    public void doDrawing(Graphics g) {
        //g.clearRect(200, 100, 200, 100);
        Graphics2D g2d = (Graphics2D) g.create();


        for(int i=0;i<shapes.size();i++)
            g2d.fill(shapes.get(i));
        /*g2d.fill(zrect);
        g2d.setPaint(new Color(255, 255, 0));
        g2d.fill(zell);*/
        g2d.dispose();
    }


    class ZEllipse extends Ellipse2D.Float {

        public ZEllipse(float x, float y, float width, float height) {
            setFrame(x, y, width, height);
        }

        public boolean isHit(float x, float y) {
            if (getBounds2D().contains(x, y) == true) {
                activatedObject = (Shape) this;
                isSthActivated = true;
            }
            return getBounds2D().contains(x, y);

        }

        public void addX(float x) {
            this.x += x;
        }

        public void addY(float y) {
            this.y += y;
        }

        public void addWidth(float w) {
            this.width += w;
        }

        public void addHeight(float h) {
            this.height += h;
        }
    }

    class ZRectangle extends Rectangle2D.Float  {

        public ZRectangle(float x, float y, float width, float height) {
            setRect(x, y, width, height);
        }

        public boolean isHit(float x, float y) {
            if (getBounds2D().contains(x, y) == true) {
                activatedObject = (Shape) this;
                isSthActivated = true;
            }
            return getBounds2D().contains(x, y);

        }

        public void addX(float x) {
            this.x += x;
        }

        public void addY(float y) {
            this.y += y;
        }

        public void addWidth(float w) {
            this.width += w;
        }

        public void addHeight(float h) {
            this.height += h;
        }
    }
/*
    class MovingAdapter extends MouseAdapter {

        private int x;
        private int y;

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if(isSthActivated==false){
                while(activatedObject==null) {//tu musimy po prostu przejs przez tablice wszystkich obiektow
                   // zrect.isHit(x, y);//tutaj dodac ze jak znajdziemy obiekt, to musimy go przepisac na wierzch
                    //zell.isHit(x, y);
                }
            }
            else{//isSthActivated==true
                if(/*activatedObject.isHit(x,y)==falsetrue){
                    isSthActivated=false;
                    activatedObject=null;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            //doMove(e);
        }
/*
        private void doMove(MouseEvent e) {

            int dx = e.getX() - x;
            int dy = e.getY() - y;
            while(!clicked) {
                zrect.isHit(x, y);
                zell.isHit(x, y);
            }
            if (clicked && obj == (Object) zrect zrect.isHit(x, y)) {

                zrect.addX(dx);
                zrect.addY(dy);
                repaint();
            }

            if (clicked && obj == (Object) zell zell.isHit(x, y)) {

                zell.addX(dx);
                zell.addY(dy);
                repaint();
            }

            x += dx;
            y += dy;
        }
    }*/
}

public class GraphicsEditor extends JApplet {
    JLabel statusbar;//can create an object or object is activated
    boolean drawing = true;
    DrawingSurface surface;

    public void init() {
        setLayout(new BorderLayout());
        createsMenu();
        surface = new DrawingSurface();
        add(surface, BorderLayout.CENTER);
        JLabel statusbar = new JLabel(" Statusbar");
        add(statusbar, BorderLayout.SOUTH);
    }



    private void createsMenu() {
        JMenuBar menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);


        JMenu drawMenu = new JMenu("Draw");

        JMenuItem circleItem = new JMenuItem("Circle");
        circleItem.setToolTipText("Draw circle");

        JMenuItem rectangleItem = new JMenuItem("Rectangle");
        rectangleItem.setToolTipText("Draw rectangle");

        JMenuItem polygonItem = new JMenuItem("Polygon");
        polygonItem.setToolTipText("Draw polygon");


        JMenuItem infoItem = new JMenuItem("Info");
        infoItem.setToolTipText("About aplet");
        infoItem.addActionListener(new InfoListener());


        drawMenu.add(circleItem);
        drawMenu.add(rectangleItem);
        drawMenu.add(polygonItem);

        menubar.add(fileMenu);
        menubar.add(drawMenu);
        menubar.add(Box.createHorizontalGlue());
        menubar.add(infoItem);

        setJMenuBar(menubar);
    }


}
