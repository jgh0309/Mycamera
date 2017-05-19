package com.example.laby.mycamera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImageView myImageView = (ImageView) findViewById(R.id.imgview);

                Bitmap myBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),
                        R.drawable.puppy);
                myImageView.setImageBitmap(myBitmap);

                BarcodeDetector detector =
                        new BarcodeDetector.Builder(getApplicationContext())    //builder까지가 클래스 이름(바코드디텍터 내부의 클래스인 빌더) ()안에 생성자의 파라미터
                                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)   // 만들어진 builder의 메서드. 반환값은 barcodedetecter
                                .build();
                if (!detector.isOperational())
                {
                    Toast.makeText(MainActivity.this, "Could not set up the detector!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);

                Barcode thisCode = barcodes.valueAt(0);
                TextView txtView = (TextView) findViewById(R.id.txtContent);
                txtView.setText(thisCode.rawValue);
            }
        });
    }
}
