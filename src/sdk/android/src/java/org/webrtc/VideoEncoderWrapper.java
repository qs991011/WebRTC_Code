/*
 *  Copyright 2017 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.webrtc;

// Explicit imports necessary for JNI generation.
import android.support.annotation.Nullable;
import java.nio.ByteBuffer;
import org.webrtc.VideoEncoder;

/**
 * This class contains the Java glue code for JNI generation of VideoEncoder.
 */
class VideoEncoderWrapper {
  @CalledByNative
  static boolean getScalingSettingsOn(VideoEncoder.ScalingSettings scalingSettings) {
    return scalingSettings.on;
  }

  @Nullable
  @CalledByNative
  static Integer getScalingSettingsLow(VideoEncoder.ScalingSettings scalingSettings) {
    return scalingSettings.low;
  }

  @Nullable
  @CalledByNative
  static Integer getScalingSettingsHigh(VideoEncoder.ScalingSettings scalingSettings) {
    return scalingSettings.high;
  }

  @CalledByNative
  static VideoEncoder.Callback createEncoderCallback(final long nativeEncoder) {
    return (EncodedImage frame, VideoEncoder.CodecSpecificInfo info)
               -> nativeOnEncodedFrame(nativeEncoder, frame.buffer, frame.encodedWidth,
                   frame.encodedHeight, frame.captureTimeNs, frame.frameType.getNative(),
                   frame.rotation, frame.completeFrame, frame.qp);
  }

  private static native void nativeOnEncodedFrame(long nativeVideoEncoderWrapper, ByteBuffer buffer,
      int encodedWidth, int encodedHeight, long captureTimeNs, int frameType, int rotation,
      boolean completeFrame, Integer qp);
}
